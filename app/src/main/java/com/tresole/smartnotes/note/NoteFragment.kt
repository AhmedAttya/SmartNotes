package com.tresole.smartnotes.note

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.tresole.smartnotes.R
import com.tresole.smartnotes.databinding.NoteFragmentBinding
import com.tresole.smartnotes.helpers.CurrentNote
import com.tresole.smartnotes.repo.Note
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
        setHasOptionsMenu(true)
        activity?.invalidateOptionsMenu()
        _binding = NoteFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return NoteViewModel(repo = Repository(context!!)) as T
            }
        })[NoteViewModel::class.java]
        if (viewModel.note.title == "") {
            binding.title.editText!!.setText("my note")
        } else {
            binding.title.editText!!.setText(viewModel.note.title)
        }
        binding.body.editText!!.setText(viewModel.note.notebody)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        Log.e("oncreate", "oncreate")
        val drawable = AppCompatResources.getDrawable(this.requireContext(),
            R.drawable.outline_favorite_24)
        val favourite = menu.findItem(R.id.favourite)
        val delete = menu.findItem(R.id.movetotrash)
        val restore = menu.findItem(R.id.restore)
        if (viewModel.note.title != "") {
            favourite.isVisible = true
            delete.isVisible = true
            if (viewModel.note.favourite == true)
                favourite.icon = drawable
            if (viewModel.note.trash == true)
                restore.isVisible = true
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.favourite -> {

                if (viewModel.note.favourite) {
                    viewModel.removefavourite()
                    activity?.invalidateOptionsMenu()
                }
                else if(viewModel.note.trash){
                    viewModel.restore()
                    viewModel.addfavourite()
                    activity?.invalidateOptionsMenu()
                }
                else {
                    viewModel.addfavourite()
                    activity?.invalidateOptionsMenu()
                }
                true
            }
            R.id.movetotrash -> {
                if (viewModel.note.trash) {
                    viewModel.delete()
                    findNavController().navigate(R.id.action_noteFragment_to_mainFragment)
                }
                else if(viewModel.note.favourite){
                    viewModel.removefavourite()
                    viewModel.movetotrash()
                    activity?.invalidateOptionsMenu()
                }
                else {
                    viewModel.movetotrash()
                    activity?.invalidateOptionsMenu()
                }
                true
            }
            R.id.restore -> {
                viewModel.restore()
                item.isVisible = false
                activity?.invalidateOptionsMenu()

                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    fun savenote() {
        val Note = Note("", "", false, false)
        CurrentNote.setCurrent(Note)
        viewModel.note.title = binding.title.editText?.text.toString()
        viewModel.note.notebody = binding.body.editText?.text.toString()
        viewModel.checkifnew()
    }

}