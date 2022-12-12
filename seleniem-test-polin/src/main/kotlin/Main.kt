import org.openqa.selenium.By
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.interactions.Actions
import java.time.Duration


fun main(args: Array<String>) {
    val driver = ChromeDriver()
    driver.get("http://localhost:8080")
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1))

    //ТЕСТ ДЛЯ ДЕЙСТВИЯ "ПОМЕТИТЬ СТУДЕНТА ЖИРНЫМ"

    driver.findElement(By.id("LinkToCourseMath")).click()
    Thread.sleep(3_000)
    val sheldon = driver.findElement(By.id("StudentItemSheldon Cooper"))
    val unmarkedFont = sheldon.getCssValue("font-weight")
    sheldon.click()
    val markedFont= sheldon.getCssValue("font-weight")
    Thread.sleep(3_000)
    driver.findElement(By.id("LinkToStudentSheldon Cooper")).click()
    Thread.sleep(3_000)
    check(driver.findElement(By.id("CourseItemMath")).getCssValue("font-weight")==markedFont)
    check(driver.findElement(By.id("CourseItemPhys")).getCssValue("font-weight")==unmarkedFont)


    //ТЕСТ ДЛЯ ДЕЙСТВИЯ "ВЫСТАВИТЬ ОЦЕНКУ СТУДЕНТУ"

    driver.findElement(By.id("LinkToCourseMath")).click()
    Thread.sleep(2_000)
    driver.findElement(By.id("CheckedGrade 5")).click()
    Thread.sleep(2_000)
    driver.findElement(By.id("LinkToStudentSheldon Cooper")).click()
    Thread.sleep(2_000)


    //ТЕСТ ДЛЯ ДЕЙСТВИЯ "УДАЛИТЬ СТУДЕНТА С КУРСА"

    driver.findElement(By.id("LinkToCourseMath")).click()
    Thread.sleep(2_000)
    driver.findElement(By.id("RemoveStudentCourse")).click()
    Thread.sleep(2_000)
    driver.findElement(By.id("RemoveStudentHoward Wolowitz")).click()
    Thread.sleep(2_000)
    driver.findElement(By.id("RemoveButton")).click()
    Thread.sleep(2_000)
    check(driver.findElement(By.id("studentList")).text.contains("Howard Wolowitz") == false)

    driver.quit()
}