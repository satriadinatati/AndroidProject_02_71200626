package com.example.notes

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class NoteAdapter(val listNote: ArrayList<Note>): RecyclerView.Adapter<NoteAdapter.NoteHolder>() {
    class NoteHolder(val v: View): RecyclerView.ViewHolder(v){
        fun bindView(note: Note){
            v.findViewById<TextView>(R.id.viewJudul).setText(note.Judul)
            v.findViewById<TextView>(R.id.viewDate).setText(note.created_at)
            v.findViewById<Button>(R.id.btnHapus).setOnClickListener { vi ->
                val firebase = FirebaseFirestore.getInstance()
                firebase.collection("notes").document(note.doc_id.toString()).delete().addOnSuccessListener {
                    Toast.makeText(vi.context, "Berhasil di hapus", Toast.LENGTH_SHORT).show()
                    val intent = Intent(vi.context, MainActivity::class.java)
                    v.context.startActivity(intent)
                }
            }
            v.setOnClickListener {
                val intent = Intent(it.context, EditNote::class.java)
                intent.putExtra("doc_id", note.doc_id)
                v.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NoteHolder(v)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        holder.bindView(listNote[position])
    }

    override fun getItemCount(): Int {
        return listNote.size
    }
}