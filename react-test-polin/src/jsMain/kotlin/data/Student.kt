package data

typealias StudentId = String

class Student (
    val firstname: String,
    val surname: String
) {
    val id: StudentId
        get() = "$firstname $surname"
}

val studentList =
    arrayOf(
        Student("Sheldon", "Cooper"),
        Student("Leonard", "Hofstadter"),
        Student("Howard", "Wolowitz")
    )