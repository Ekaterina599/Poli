import io.kotest.assertions.withClue
import io.kotest.core.spec.style.scopes.StringSpecScope
import io.kotest.matchers.shouldBe
import redux.*

val stateTest: suspend StringSpecScope.() -> Unit = {
    val store = createStore(
        appReducer,
        testState(),
        rEnhancer(),
    )

    val s0 = store.state
    //Кол-во студентов и курсов по 3 (размер массива курс должен быть 3)
    s0.courses.size shouldBe 3
    s0.students.size shouldBe 3
    s0.grade.size shouldBe 0
    val sheldon = s0.students.firstOrNull {
        it.firstname == "Sheldon"
    }
    check(sheldon != null)
    val howard = s0.students.firstOrNull {
        it.firstname == "Howard"
    }
    check(howard != null)
    val math = s0.courses.firstOrNull {
        it.name == "Math"
    }

    check(math != null)
    withClue("Check init state") {
        math.marked[math.students.indexOfFirst { it == sheldon.id }] shouldBe false
    }

    store.dispatch(MarkStudent(math.id, sheldon.id))
    val s1 = store.state
    val math1 = s1.courses.firstOrNull {
        it.name == "Math"
    }
    check(math1 != null)
    withClue("Mark Sheldon Math") {
        math1.marked[math1.students.indexOfFirst { it == sheldon.id }] shouldBe true
    }

    val students = s1.getStudentsById(arrayOf(sheldon.id, howard.id))
    withClue("Check getStudentById") {
        students.size shouldBe 2
        students.find { it.firstname == "Sheldon" }?.id shouldBe "Sheldon Cooper"
    }

    //Проверка действия ДОБАВЛЕНИЕ ОЦЕНКИ
    store.dispatch(AddGrade(math.id, sheldon.id, 5))
    val s2 = store.state
    val math2 = s2.courses.firstOrNull { it.name == "Math" }
    check(math2 != null)
    withClue("Grade 5 Sheldon Math") {
        math2.grades[math2.students.indexOfFirst { it == sheldon.id }] shouldBe 5 }

    //Проверка селектора getCoursesGrade
    val grades = s2.getCoursesGrade(sheldon)
    withClue("Check getCoursesGradeById") {
        grades.first {it.first.name == "Math"}.second shouldBe 5 }

    //Проверка действия УДАЛЕНИЕ СТУДЕНТА C КУРСА
    store.dispatch(RemoveStudent(math.id, sheldon.id))
    val s3 = store.state
    val math3 = s3.courses.firstOrNull { it.name == "Phys" }
    check(math3 != null)
    withClue("Sheldon REMOVE Math") {
        !math3.students.contains (sheldon.id) }
}