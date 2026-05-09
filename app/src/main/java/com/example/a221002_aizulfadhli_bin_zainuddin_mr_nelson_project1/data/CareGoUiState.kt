package com.example.a221002_aizulfadhli_bin_zainuddin_mr_nelson_project1.data


data class HealthEntry(
    val id: Int,
    val name: String,
    val symptoms: String,
    val mood: String,
    val date: String
)

data class CareGoUiState(
    val userName: String = "",
    val selectedMood: String = "Select a mood",
    val symptomNotes: String = "", //field for input
    //list to store multiple items
    val healthHistory: List<HealthEntry> = emptyList(),
    val isExpanded: Boolean = false
)