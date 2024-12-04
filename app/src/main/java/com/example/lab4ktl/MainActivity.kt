package com.example.lab4ktl

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    private lateinit var tvStatus: TextView
    private lateinit var btnViewRecords: Button
    private val apiUrl = "https://dummyjson.com/products"
    private var productList = ArrayList<String>() // Список продуктов

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvStatus = findViewById(R.id.tvStatus)
        btnViewRecords = findViewById(R.id.btnViewRecords)

        // Асинхронный запрос
        FetchProductsTask().execute()

        // Переход ко второй активности
        btnViewRecords.setOnClickListener {
            val intent = Intent(this, ViewRecordsActivity::class.java)
            intent.putStringArrayListExtra("productList", productList)
            startActivity(intent)
        }
    }

    inner class FetchProductsTask : AsyncTask<Void, Void, String?>() {
        override fun doInBackground(vararg params: Void?): String? {
            return try {
                val connection = URL(apiUrl).openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connect()
                connection.inputStream.bufferedReader().readText()
            } catch (e: Exception) {
                null
            }
        }

        override fun onPostExecute(result: String?) {
            if (!result.isNullOrEmpty()) {
                val json = JSONObject(result)
                val products: JSONArray = json.getJSONArray("products")

                // Формируем список продуктов
                for (i in 0 until products.length()) {
                    val product = products.getJSONObject(i)
                    val title = product.getString("title")
                    val price = product.getDouble("price")
                    productList.add("Продукт: $title, Цена: $price")
                }

                tvStatus.text = "Найдено продуктов: ${productList.size}"
            } else {
                tvStatus.text = "Ошибка получения данных"
            }
        }
    }
}
