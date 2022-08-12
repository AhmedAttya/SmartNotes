package com.tresole.smartnotes.note

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
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
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.menu_main, menu)
                val drawable = AppCompatResources.getDrawable(requireContext(),
                    R.drawable.outline_favorite_24)
                val favourite = menu.findItem(R.id.favourite)
                val delete = menu.findItem(R.id.movetotrash)
                val restore = menu.findItem(R.id.restore)
                if (viewModel.note.title != "") {
                    favourite.isVisible = true
                    delete.isVisible = true
                    if (viewModel.note.favourite)
                        favourite.icon = drawable
                    if (viewModel.note.trash)
                        restore.isVisible = true
                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                Log.e("gg", menuItem.itemId.toString())
                return when (menuItem.itemId) {
                    R.id.favourite -> {
                        Log.e("gg", "called")
                        if (viewModel.note.favourite) {
                            viewModel.removefavourite()
                            menuHost.invalidateMenu()
                        } else if (viewModel.note.trash) {
                            viewModel.restore()
                            viewModel.addfavourite()
                            menuHost.invalidateMenu()
                        } else {
                            viewModel.addfavourite()
                            menuHost.invalidateMenu()

                        }
                        true
                    }
                    R.id.movetotrash -> {
                        if (viewModel.note.trash) {
                            viewModel.delete()
                            findNavController().navigate(R.id.action_noteFragment_to_mainFragment)
                        } else if (viewModel.note.favourite) {
                            viewModel.removefavourite()
                            viewModel.movetotrash()
                            menuHost.invalidateMenu()
                        } else {
                            viewModel.movetotrash()
                            menuHost.invalidateMenu()
                        }
                        true
                    }
                    R.id.restore -> {
                        viewModel.restore()
                        menuItem.isVisible = false
                        menuHost.invalidateMenu()

                        true
                    }
                    else -> true
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        if (viewModel.note.title == "") {
            binding.title.editText!!.setText(getString(R.string.mynote))
        } else {
            binding.title.editText!!.setText(viewModel.note.title)
        }
        binding.body.editText!!.setText(viewModel.note.notebody)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    fun savenote() {
        val Note = Note("", "", false, false)
        CurrentNote.setCurrent(Note)
        viewModel.note.title = binding.title.editText?.text.toString()
        viewModel.note.notebody = binding.body.editText?.text.toString()
        viewModel.checkifnew()
    }

}