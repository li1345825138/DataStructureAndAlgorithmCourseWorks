@ECHO OFF

IF "%1" == "" GOTO noarg

SET CHECKSTYLE=
SET jar=C:\Users\Admin\Desktop\project\Java\DataStructureAndAlgorithmCourseWorks\libs\checkstyle-8.21-all.jar
SET lft=C:\Users\Admin\Desktop\project\Java\DataStructureAndAlgorithmCourseWorks\libs\checkstyle-lift.jar
SET xml=C:\Users\Admin\Desktop\project\Java\DataStructureAndAlgorithmCourseWorks\libs\checkstyle.xml
SET bas=C:\Users\Admin\Desktop\project\Java\DataStructureAndAlgorithmCourseWorks
SET sup=C:\Users\Admin\Desktop\project\Java\DataStructureAndAlgorithmCourseWorks\libs\checkstyle-suppressions.xml


for %%x in (%*) do (
    ECHO %%~x:
    java -cp "%lft%;%jar%" "-Dbasedir=%bas%" "-Dsuppressions=%sup%" com.puppycrawl.tools.checkstyle.Main -c "%xml%" %%~x
)

GOTO end

:noarg
echo Usage: check_style.bat ^<java files^>

:end
