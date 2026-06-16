import java.util.Scanner

class Menu(
    private val title: String,
    private val items: List<MenuItem>,
    private val scanner: Scanner
) {

    fun show(): Boolean {
        while (true) {
            println(title)
            items.forEachIndexed { index, item ->
                println("$index. ${item.name}")
            }
            print("Выберите пункт: ")
            val input = scanner.nextLine().trim()

            if (input.isEmpty()) {
                println("Ошибка: введите номер пункта.")
                continue
            }

            val choice = input.toIntOrNull()
            if (choice == null) {
                println("Ошибка: введите цифру.")
                continue
            }

            if (choice !in items.indices) {
                println("Ошибка: пункта с таким номером нет.")
                continue
            }

            val selected = items[choice]
            if (selected.isExit) return true

            selected.action?.invoke()
            return false
        }
    }
}

class MenuItem(
    val name: String,
    val isExit: Boolean = false,
    val action: (() -> Unit)? = null
)