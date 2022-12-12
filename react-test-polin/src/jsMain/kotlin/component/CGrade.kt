package component

import csstype.*
import data.Grade
import react.FC
import react.Props
import emotion.react.css
import react.dom.html.InputType
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.option
import react.dom.html.ReactHTML.select
import react.redux.useSelector
import react.useContext
import redux.AppState

external interface GradeProps : Props {
    var grade: Grade
    var setGrade: (Grade) -> Unit
}


val CGrade =FC<GradeProps>("Grade") { props ->
    val color = useSelector { state: AppState -> state.color }
    for (i in 1..5) {
        input {
            this.checked = false
            value = "$i"
            if (i == props.grade) {
                this.checked = true
                css {
                    boxShadow =
                        BoxShadow(
                            2.px, 2.px,
                            Color(color[props.grade - 1])
                        )
                }
            }
            type = InputType.checkbox
            onChange = { props.setGrade(it.target.let { (value as String).toInt() }) }
        }
    }
}