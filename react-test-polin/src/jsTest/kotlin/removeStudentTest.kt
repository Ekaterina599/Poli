
import component.CGrade
import io.kotest.core.spec.style.scopes.StringSpecScope
import io.kotest.matchers.shouldBe
import kotlinx.browser.document
import kotlinx.coroutines.delay
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLSelectElement
import org.w3c.dom.get
import react.FC
import react.Props
import react.create
import react.dom.client.createRoot
import react.redux.Provider
import redux.*
//ТЕСТ УДАЛЕНИЕ СТУДЕНТА С КУРСА
val removeStudentTest: suspend StringSpecScope.() -> Unit = {
    //Cоздаем хранилище (окружение компонента)
    val store = createStore(
        appReducer,
        testState(),
        rEnhancer(),
    )
    //Создаем div, куда мы поместим наш компонент в браузере
    val container = document.createElement("div")
    document.body!!.appendChild(container)
    val root = createRoot(container)

    val sheldon = store.state.getStudentsById(arrayOf("Sheldon Cooper")).first()
    val cSheldon = FC { _: Props ->
        Provider {
            this.store = store
            val comp = CGrade.create {
                grade = store.state.getCoursesGrade(sheldon).first { it.first.name == "Math" }.second
                setGrade = { store.dispatch(AddGrade("Math", sheldon.id, it)) }
            }
            children = comp
        }
    }
    root.render(cSheldon.create())
    delay(1)
    console.log(container)
    val helloDiv = container.childNodes[0] as HTMLInputElement
    helloDiv.value shouldBe "1"

    store.dispatch(RemoveStudent("Math", sheldon.id))
    val gradeMath1 = store.state.courses.firstOrNull { it.name == "Phys" }
    !gradeMath1!!.students.contains(sheldon.id)
    delay(1)
    console.log(container)
    val helloDiv2 = container.childNodes[0] as HTMLInputElement
    helloDiv2.value shouldBe "1"
}
