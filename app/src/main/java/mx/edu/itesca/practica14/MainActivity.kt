package mx.edu.itesca.practica14

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import mx.edu.itesca.practica14.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
//    val db = Firebase.firestore
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: TareaAdapter
    private lateinit var viewModel: TareaViewModel

    var tareaEdit = Tarea()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[TareaViewModel::class.java]

        viewModel.listaTarea.observe(this){
            tareas -> setupRecylerView(tareas)
        }

        binding.btnAgregarTarea.setOnClickListener {
            val tarea = Tarea(
                titulo = binding.etTitulo.text.toString(),
                descripcion = binding.etDescripcion.text.toString()
            )

            viewModel.agregarTareas(tarea)

            binding.etTitulo.setText("")
            binding.etDescripcion.setText("")
        }

        binding.btnActualizarTarea.setOnClickListener {
           tareaEdit.titulo = ""
           tareaEdit.descripcion = ""

           tareaEdit.titulo = binding.etTitulo.text.toString()
           tareaEdit.descripcion = binding.etDescripcion.text.toString()

           viewModel.actualizarTareas(tareaEdit)
        }

    }

    fun setupRecylerView(listaTarea: List<Tarea>){
        adapter = TareaAdapter(listaTarea, ::borrarTarea, ::actualizarTarea)
        binding.rvTareas.adapter = adapter
    }

    fun borrarTarea(id: String){
        viewModel.borrarTareas(id)
    }

    fun actualizarTarea(tarea: Tarea){
        tareaEdit = tarea

        binding.etTitulo.setText(tareaEdit.titulo)
        binding.etDescripcion.setText(tareaEdit.descripcion)
    }
}