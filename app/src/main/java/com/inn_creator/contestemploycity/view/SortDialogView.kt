package com.inn_creator.contestemploycity.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import com.inn_creator.contestemploycity.R

class SortDialogView(
    context: Context,
    private val onClick: (bySort: BySort, typeSort: TypeSort) -> Unit,
) : Dialog(context) {

    private var typeSort = TypeSort.ALPHABET
    private var bySort = BySort.ASC


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sort_dialog)

        findViewById<RadioGroup>(R.id.rg_by).setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.desc -> bySort = BySort.DESC
                R.id.asc -> bySort = BySort.ASC
            }
        }

        findViewById<RadioGroup>(R.id.rg_type).setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.alphabet -> typeSort = TypeSort.ALPHABET
                R.id.value -> typeSort = TypeSort.VALUE
            }
        }

        findViewById<Button>(R.id.done).setOnClickListener {
            onClick(bySort, typeSort)
            dismiss()
        }
    }
}

enum class TypeSort {
    ALPHABET, VALUE
}

enum class BySort {
    DESC, ASC
}