package com.tresole.smartnotes.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tresole.smartnotes.R
import com.tresole.smartnotes.databinding.MainFragmentBinding
import com.tresole.smartnotes.repo.Note
import com.tresole.smartnotes.repo.Repository

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MainFragment : Fragment(),Noteclicklistener {
    private lateinit var viewModel: MainViewModel
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(repo= Repository(context!!)) as T
            }
        })[MainViewModel::class.java]
        var adapter: NoteAdapter
        val RecyclerView=binding.recyclerview
       val layoutManager=GridLayoutManager(this.context,3)
        RecyclerView.layoutManager=layoutManager
        viewModel.load()
        viewModel.list.observe(viewLifecycleOwner) {
         adapter= NoteAdapter(it,this)
         RecyclerView.adapter=adapter
         adapter.notifyDataSetChanged()
        }




    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onclick(note: Note) {
        Toast.makeText(context, "ggez clicked", Toast.LENGTH_LONG).show()
        viewModel.setcurrent(note)
        findNavController().navigate(R.id.action_mainFragment_to_noteFragment)
    }
}