package com.example.a221002_aizulfadhli_bin_zainuddin_mr_nelson_project1

import androidx.lifecycle.ViewModel
import com.example.a221002_aizulfadhli_bin_zainuddin_mr_nelson_project1.data.CareGoUiState
import com.example.a221002_aizulfadhli_bin_zainuddin_mr_nelson_project1.data.HealthEntry
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CareGoViewModel : ViewModel() {
    //the private "internal" state
    private val _uiState = MutableStateFlow(CareGoUiState())
    //the public "read-only" state the UI watches
    val uiState: StateFlow<CareGoUiState> = _uiState.asStateFlow()

    //function to update the name
    fun updateName(newName: String) {
        _uiState.update { currentState ->
            currentState.copy(userName = newName)
        }
    }

    //function to update the mood
    fun updateMood(newMood: String) {
        _uiState.update { it.copy(selectedMood = newMood) }
    }

    fun updateSymptoms(newSymptoms: String) {
        _uiState.update { it.copy(symptomNotes = newSymptoms) }
    }

    //function to ADD an item to the list : project requirement
    fun saveEntry() {
        //date formatter to show when it was logged
        val currentDate = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date())
        val newEntry = HealthEntry(
            id = _uiState.value.healthHistory.size + 1,
            name = _uiState.value.userName,
            mood = _uiState.value.selectedMood,
            symptoms = _uiState.value.symptomNotes, //save notes
            date = currentDate //Dynamic Date
        )

        _uiState.update { currentState ->
            currentState.copy(
                healthHistory = currentState.healthHistory + newEntry,
                //Reset inputs for the next entry
                userName = "",
                selectedMood = "Select a mood",
                symptomNotes = "",
                isExpanded = false
            )
        }
    }

    //to handle the expandable card animation
    fun toggleExpansion() {
        _uiState.update { currentState ->
            currentState.copy(isExpanded = !currentState.isExpanded)
        }
    }
}