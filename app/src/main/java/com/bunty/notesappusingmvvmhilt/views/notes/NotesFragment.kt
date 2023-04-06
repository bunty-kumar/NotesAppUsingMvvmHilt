package com.bunty.notesappusingmvvmhilt.views.notes

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bunty.notesappusingmvvmhilt.R
import com.bunty.notesappusingmvvmhilt.databinding.FragmentNotesBinding
import com.bunty.notesappusingmvvmhilt.models.NoteRequest
import com.bunty.notesappusingmvvmhilt.models.NoteResponse
import com.bunty.notesappusingmvvmhilt.utils.NetworkResult
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotesFragment : Fragment() {

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!
    private var note: NoteResponse? = null
    private val notesViewModel by viewModels<NotesViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setInitialData()
        bindHandlers()
        bindObservers()
    }

    private fun bindObservers() {
        notesViewModel.statusLiveData.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = false
            binding.btnSubmit.isVisible = true
            when (it) {
                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                    binding.btnSubmit.isVisible = false
                }
                is NetworkResult.Success -> {
                    findNavController().popBackStack()
                }
            }
        }
    }

    private fun bindHandlers() {
        binding.btnDelete.setOnClickListener {
            note?.let {
                notesViewModel.deleteNote(it._id)
            }
        }
        binding.btnSubmit.setOnClickListener {
            val title = binding.txtTitle.text.toString().trim()
            val desc = binding.txtDescription.text.toString().trim()
            val noteRequest = NoteRequest(title, desc)
            if (note != null) {
                notesViewModel.updateNote(note!!._id, noteRequest)
            } else {
                notesViewModel.createNote(noteRequest)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setInitialData() {
        val jsonNote = arguments?.getString("note")
        if (jsonNote != null) {
            note = Gson().fromJson(jsonNote, NoteResponse::class.java)
            note?.let {
                binding.txtTitle.setText(it.title)
                binding.txtDescription.setText(it.description)
            }
        } else {
            binding.addEditText.text = "Add Note"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}