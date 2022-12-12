package redux

import AddColor
import AddCourse
import AddGrade
import AddStudent
import AddStudentToCourse
import ChangeMode
import ColorAction
import CourseAction
import MarkStudent
import ModeAction
import RemoveStudent
import StudentAction
import react.Reducer
import tools.plus
import tools.replace

val appReducer: Reducer<AppState, RAction> =
    { state, action ->
        when (action) {
            is CourseAction -> AppState(
                courseReducer(state.courses, action),
                state.students,
                state.mode,
                state.grade,
                state.color
            )

            is StudentAction -> AppState(
                state.courses,
                studentReducer(state.students, action),
                state.mode,
                state.grade,
                state.color
            )

            is ModeAction -> AppState(
                state.courses,
                state.students,
                modeReducer(state.mode, action),
                state.grade,
                state.color
            )

            is AddColor -> AppState(
                state.courses,
                state.students,
                state.mode,
                state.grade,
                colorReducer(state.color, action)
            )

            else -> state
        }
    }

val courseReducer: Reducer<CourseState, CourseAction> =
    { state, action ->
        when (action) {
            is MarkStudent -> state.replace(
                { _, course -> course.id == action.courseId },
                { course ->
                    course.copy(
                        marked = course.marked.replace(
                            { index, _ -> course.students[index] == action.studentId },
                            { mark -> !mark }
                        )
                    )
                }
            )

            is AddCourse -> state + action.course

            is AddStudentToCourse -> state.replace(
                { _, course -> course.id == action.courseId },
                { course ->
                    course.copy(
                        students = course.students + action.studentId,
                        marked = course.marked + false,
                        grades = course.grades + 1
                    )
                }
            )

            is RemoveStudent -> state.replace(
                { _, course -> course.id == action.courseId },
                { course ->
                    course.copy(
                        students = course.students.filter {it != action.studentId}.toTypedArray()
                        //добавляются студенты, которых не выбрали как удаленные
                    )
                }
            )

            is AddGrade -> state.replace(
                { _, course -> course.id == action.courseId },
                { course ->
                    course.copy(
                        grades = course.grades.replace(
                            { index, _ -> course.students[index] == action.studentId },
                            { action.grade}
                        )
                    )
                }
            )
            else -> state
        }
    }

val studentReducer: Reducer<StudentState, StudentAction> =
    { state, action ->
        when (action) {
            is AddStudent -> state + action.student
            else -> state
        }
    }

val modeReducer: Reducer<ModeState, ModeAction> =
    { state, action ->
        when (action) {
            is ChangeMode -> !state
            else -> state
        }
    }

val colorReducer: Reducer<ColorState, ColorAction> =
    { state, action ->
        when (action) {
            is AddColor ->state.replace(
                {index,_->index == action.index},
                {action.color}
            )
            else -> state
        }
    }

