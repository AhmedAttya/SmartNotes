package com.tresole.smartnotes.note

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.tresole.smartnotes.R
import com.tresole.smartnotes.databinding.NoteFragmentBinding
import com.tresole.smartnotes.main.MainViewModel
import com.tresole.smartnotes.repo.Repository

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class NoteFragment : Fragment() {
    private lateinit var viewModel: NoteViewModel
    private var _binding: NoteFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = NoteFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return NoteViewModel(repo= Repository(context!!)) as T
            }
        })[NoteViewModel::class.java]
        binding.title.editText!!.setText(viewModel.note.title)
        binding.body.editText!!.setText(viewModel.note.notebody)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}