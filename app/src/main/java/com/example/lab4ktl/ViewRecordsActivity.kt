package com.example.lab4ktl

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class ViewRecordsActivity : AppCompatActivity() {
    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_records)

        listView = findViewById(R.id.listView)

        // Получение списка продуктов из Intent
        val productList = intent.getStringArrayListExtra("productList")

        // Отображение продуктов в ListView
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            productList ?: listOf("Данные отсутствуют")
        )
        listView.adapter = adapter
    }
}
