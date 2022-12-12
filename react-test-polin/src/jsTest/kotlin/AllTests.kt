import io.kotest.core.spec.style.StringSpec

class AllTests : StringSpec({

    "StudentItem test"(studentItemTest)
    "State test"(stateTest)
    "Grade test" (gradeTest)
    "RemoveStudent test" (removeStudentTest)
})



