import component.CGrade
import io.kotest.core.spec.style.scopes.StringSpecScope
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.haveLength
import kotlinx.browser.document
import kotlinx.coroutines.delay
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.get
import react.FC
import react.Props
import react.create
import react.dom.client.createRoot
import react.redux.Provider
import redux.*
//ТЕСТ ДОБАВЛЕНИЯ ОЦЕНКИ
val gradeTest: suspend StringSpecScope.() -> Unit = {
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
   // val leonard= store.state.getStudentsById(arrayOf("Leonard Hofstadter")).first()
    val cGrade = FC { _: Props ->
        Provider {
            this.store = store
            val comp = CGrade.create {
                grade = store.state.getCoursesGrade(sheldon).first { it.first.name == "Math" }.second
                setGrade = { store.dispatch(AddGrade("Math", sheldon.id, it)) }
            }
            children = comp
        }
    }
    root.render(cGrade.create())
    delay(1)
    console.log(container)
    val helloDiv = container.childNodes[0] as HTMLInputElement
    helloDiv.checked shouldBe true
    var classNameGrade = helloDiv.classList

    store.dispatch(AddColor(3, "ff00ff"))
    store.dispatch(AddGrade("Math", sheldon.id, 4))
    root.render(cGrade.create())
    delay(1)
    console.log(container)
    val helloDiv4 = container.childNodes[0] as HTMLInputElement
    helloDiv4.classList shouldBe  classNameGrade
}