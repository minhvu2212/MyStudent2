package com.example.mystudent

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
  private lateinit var studentList: MutableList<StudentModel>
  private lateinit var adapter: StudentAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    studentList = mutableListOf(
      StudentModel("Nguyễn Văn An", "SV001"),
      StudentModel("Trần Thị Bảo", "SV002"),
      StudentModel("Lê Hoàng Cường", "SV003"),
      StudentModel("Phạm Thị Dung", "SV004"),
      StudentModel("Đỗ Minh Đức", "SV005"),
      StudentModel("Vũ Thị Hoa", "SV006"),
      StudentModel("Hoàng Văn Hải", "SV007"),
      StudentModel("Bùi Thị Hạnh", "SV008"),
      StudentModel("Đinh Văn Hùng", "SV009"),
      StudentModel("Nguyễn Thị Linh", "SV010"),
      StudentModel("Phạm Văn Long", "SV011"),
      StudentModel("Trần Thị Mai", "SV012"),
      StudentModel("Lê Thị Ngọc", "SV013"),
      StudentModel("Vũ Văn Nam", "SV014"),
      StudentModel("Hoàng Thị Phương", "SV015"),
      StudentModel("Đỗ Văn Quân", "SV016"),
      StudentModel("Nguyễn Thị Thu", "SV017"),
      StudentModel("Trần Văn Tài", "SV018"),
      StudentModel("Phạm Thị Tuyết", "SV019"),
      StudentModel("Lê Văn Vũ", "SV020")
    )

    val recyclerView: RecyclerView = findViewById(R.id.recycler_view_students)
    recyclerView.layoutManager = LinearLayoutManager(this)

    adapter = StudentAdapter(studentList) { view, position ->
      // Đảm bảo rằng bạn đã cập nhật đúng contextMenuPosition
      adapter.contextMenuPosition = position
      registerForContextMenu(view)
      view.showContextMenu()
    }
    recyclerView.adapter = adapter
  }

  // Thiết lập OptionMenu
  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.menu_add_new -> {
        val intent = Intent(this, AddStudentActivity::class.java)
        startActivityForResult(intent, REQUEST_CODE_ADD)
        return true
      }
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onCreateContextMenu(
    menu: ContextMenu,
    v: View,
    menuInfo: ContextMenu.ContextMenuInfo?
  ) {
    super.onCreateContextMenu(menu, v, menuInfo)
    menuInflater.inflate(R.menu.context_menu, menu)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (resultCode == RESULT_OK && data != null) {
      when (requestCode) {
        REQUEST_CODE_ADD -> {
          val newStudent = data.getParcelableExtra<StudentModel>("student")
          if (newStudent != null) {
            studentList.add(newStudent)
            adapter.notifyItemInserted(studentList.size - 1)
          }
        }
        REQUEST_CODE_EDIT -> {
          val editedStudent = data.getParcelableExtra<StudentModel>("student")
          val position = data.getIntExtra("position", -1)
          if (position != -1 && editedStudent != null) {
            studentList[position] = editedStudent
            adapter.notifyItemChanged(position)
          }
        }
      }
    }
  }

  override fun onContextItemSelected(item: MenuItem): Boolean {
    val position = adapter.contextMenuPosition
    when (item.itemId) {
      R.id.menu_edit -> {
        val intent = Intent(this, EditStudentActivity::class.java)
        intent.putExtra("student", studentList[position])
        intent.putExtra("position", position)
        startActivityForResult(intent, REQUEST_CODE_EDIT)
      }
      R.id.menu_remove -> {
        studentList.removeAt(position)
        adapter.notifyItemRemoved(position)
      }
    }
    return super.onContextItemSelected(item)
  }

  companion object {
    const val REQUEST_CODE_ADD = 1001
    const val REQUEST_CODE_EDIT = 1002
  }
}



