package com.projemanag.activities

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.projemanag.R
import com.projemanag.adapters.MemberListItemsAdapter
import com.projemanag.databinding.ActivityMembersBinding
import com.projemanag.firebase.FirestoreClass
import com.projemanag.model.Board
import com.projemanag.model.User
import com.projemanag.utils.Constants

class MembersActivity : BaseActivity() {

    private lateinit var binding: ActivityMembersBinding
    private lateinit var mBoardDetails: Board
    private lateinit var mAssignedMembers: ArrayList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMembersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra(Constants.BOARD_DETAIL)) {
            mBoardDetails = intent.getParcelableExtra<Board>(Constants.BOARD_DETAIL)!!
        }

        setupActionBar()
        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().getAssignedMembersListDetails(this@MembersActivity, mBoardDetails.assignedTo)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add_member, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add_member -> {
                showAddMemberDialog()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showAddMemberDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Add Member")

        val emailInput = EditText(this)
        emailInput.hint = "Enter member email"
        emailInput.setPadding(16, 16, 16, 16)
        builder.setView(emailInput)

        builder.setPositiveButton("Add") { dialog, _ ->
            val email = emailInput.text.toString().trim()
            if (email.isNotEmpty()) {
                showProgressDialog(resources.getString(R.string.please_wait))
                FirestoreClass().getMemberDetails(this@MembersActivity, email)
            } else {
                Toast.makeText(this, "Enter email", Toast.LENGTH_SHORT).show()
            }
            dialog.dismiss()
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        builder.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == MEMBERS_REQUEST_CODE) {
            showProgressDialog(resources.getString(R.string.please_wait))
            FirestoreClass().getAssignedMembersListDetails(this@MembersActivity, mBoardDetails.assignedTo)
        } else {
            Toast.makeText(this@MembersActivity, "No members added.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbarMembersActivity)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        }
        binding.toolbarMembersActivity.setNavigationOnClickListener { onBackPressed() }
    }

    fun setupMembersList(list: ArrayList<User>) {
        mAssignedMembers = list

        hideProgressDialog()

        binding.rvMembersList.layoutManager = LinearLayoutManager(this@MembersActivity)
        binding.rvMembersList.setHasFixedSize(true)

        val adapter = MemberListItemsAdapter(this@MembersActivity, mAssignedMembers)
        binding.rvMembersList.adapter = adapter

        adapter.setOnClickListener(object : MemberListItemsAdapter.OnClickListener {
            override fun onClick(position: Int, user: User, action: String) {
                if (action == Constants.SELECT) {
                    if (!mBoardDetails.assignedTo.contains(user.id)) {
                        mBoardDetails.assignedTo.add(user.id)
                    }
                } else {
                    mBoardDetails.assignedTo.remove(user.id)

                    for (i in mAssignedMembers.indices) {
                        if (mAssignedMembers[i].id == user.id) {
                            mAssignedMembers[i].selected = false
                        }
                    }
                }
                adapter.notifyDataSetChanged()
            }
        })

    }


    fun memberAddedSuccess() {
        hideProgressDialog()
        Handler(Looper.getMainLooper()).postDelayed({
            setResult(Activity.RESULT_OK)
            finish()
        }, 500)
    }

    companion object {
        const val MEMBERS_REQUEST_CODE: Int = 13
    }
    fun memberFound(user: User) {
        Log.e("MembersActivity", "Member found: ${user.name} (${user.id})")
        if (!mBoardDetails.assignedTo.contains(user.id)) {
            mBoardDetails.assignedTo.add(user.id)
            Log.e("MembersActivity", "Assigned to list: ${mBoardDetails.assignedTo}")
            FirestoreClass().assignMemberToBoard(this@MembersActivity, mBoardDetails, user)

            Handler(Looper.getMainLooper()).postDelayed({
                Log.e("MembersActivity", "Calling refresh...")
                FirestoreClass().getAssignedMembersListDetails(this@MembersActivity, mBoardDetails.assignedTo)
            }, 500)
        } else {
            hideProgressDialog()
            Toast.makeText(this, "Member already added", Toast.LENGTH_SHORT).show()
        }
    }

    fun memberNotFound() {
        hideProgressDialog()
        Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show()
    }
}