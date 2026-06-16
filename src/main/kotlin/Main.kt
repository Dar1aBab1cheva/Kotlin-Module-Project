import java.util.Scanner

val archives = mutableListOf<Archive>()
val scanner = Scanner(System.`in`)

fun main() {
    while (true) {
        val archiveItems = mutableListOf<MenuItem>()
        archiveItems.add(MenuItem("Создать архив") { createArchive() })
        archives.forEach { archive ->
            archiveItems.add(
                MenuItem(archive.name) { showNotesMenu(archive) }
            )
        }
        archiveItems.add(MenuItem("Выход", isExit = true))

        val mainMenu = Menu("Выбор архива:", archiveItems, scanner)
        // Если show() вернул true – был выбран "Выход", завершаем программу
        if (mainMenu.show()) break
    }
}

fun createArchive() {
    println("Введите имя архива (не пустое):")
    while (true) {
        val name = scanner.nextLine().trim()
        if (name.isNotEmpty()) {
            archives.add(Archive(name))
            println("Архив '$name' создан.")
            return
        } else {
            println("Имя не может быть пустым. Попробуйте снова:")
        }
    }
}


fun showNotesMenu(archive: Archive) {
    while (true) {
        val noteItems = mutableListOf<MenuItem>()
        noteItems.add(MenuItem("Создать заметку") { createNote(archive) })
        archive.notes.forEach { note ->
            noteItems.add(
                MenuItem(note.name) { showNote(note) }
            )
        }
        noteItems.add(MenuItem("Назад", isExit = true))

        val notesMenu = Menu("Заметки в архиве '${archive.name}':", noteItems, scanner)
        // Если выбрано "Назад" – выходим из этого меню и возвращаемся к списку архивов
        if (notesMenu.show()) break
    }
}

fun createNote(archive: Archive) {
    val name = readNonEmptyString("имя заметки")
    val text = readNonEmptyString("текст заметки")
    archive.notes.add(Note(name, text))
    println("Заметка '$name' создана в архиве '${archive.name}'.")
}

fun readNonEmptyString(prompt: String): String {
    while (true) {
        println("Введите $prompt (не пустое):")
        val input = scanner.nextLine().trim()
        if (input.isNotEmpty()) {
            return input
        } else {
            println("Ошибка: $prompt не может быть пустым. Попробуйте снова.")
        }
    }
}

fun showNote(note: Note) {
    println("=== Заметка: ${note.name} ===")
    println(note.text)
    println("Нажмите Enter, чтобы вернуться...")
    scanner.nextLine()
}