package mohagheghi.mahdi.basalamfirstapp.util

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import mohagheghi.mahdi.basalamfirstapp.R
import mohagheghi.mahdi.basalamfirstapp.databinding.LoadingBinding

class Loading(context: Context) {

    private var dialog: AlertDialog

    init {
        val builder = AlertDialog.Builder(context)
        val binding = LoadingBinding.inflate(LayoutInflater.from(context))
        builder.setView(binding.root)
        dialog = builder.create()
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.bg_dialog_transparent)
    }

    fun show() {
        dialog!!.show()
    }

    fun hide() {
        dialog!!.dismiss()
    }
}