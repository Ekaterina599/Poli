package component

import AddColor
import csstype.*
import emotion.react.css
import org.w3c.dom.HTMLInputElement
import react.*
import react.dom.events.ChangeEventHandler
import react.dom.html.InputType
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.br
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h3
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.label
import react.redux.useDispatch
import react.redux.useSelector
import redux.AppState
import redux.RAction


val CColor = FC<Props> {
    val listColor = useSelector{state: AppState -> state.color }
    val dispatch = useDispatch<RAction, Unit>()
    val ref = useRef<HTMLInputElement>()
    val refs = useRef<HTMLInputElement>()
    h3 { +"Colors" }
    listOf(1,2,3,4,5).mapIndexed { indexGrade, grade ->
        div {
            input { this.ref = ref }
        }
        input {
            this.ref = refs
            value = Color(listColor[indexGrade])
            onChange = { dispatch(AddColor(indexGrade, it.target.value)) }
        }

    }
}
