package com.example.gastos.adapters  // Ajusta el paquete

import android.content.Context
import android.widget.ArrayAdapter

class CategorySpinnerAdapter(context: Context, categories: Array<String>) :
    ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, categories) {

    init {
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    }
}