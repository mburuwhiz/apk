package com.whizpos.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CategoryPills(
    categories: List<String>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    LazyRow(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)) {
        items(categories) {
            Button(
                onClick = { onCategorySelected(it) },
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text(it)
            }
        }
    }
}