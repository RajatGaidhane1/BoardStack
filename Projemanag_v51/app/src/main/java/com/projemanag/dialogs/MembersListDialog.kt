package com.projemanag.dialogs

import android.app.Dialog
import android.content.Context

abstract class MembersListDialog(context: Context) : Dialog(context) {
    // TODO: Fix members dialog - layout missing

    abstract fun membersList(): ArrayList<String>
    abstract fun addClicked(item: String)
}