@echo ############## start copying REST files ##############
@RD /S /Q "..\JetpackComposeGit\app\src\main\java\org"
@Xcopy .\swagger-generated\src\main\kotlin ..\android\app\src\main\java /E/H/C/I/Y
@RD /S /Q ".\swagger-generated"
@Xcopy .\changed_files\OffsetDateTimeAdapter.kt ..\android\app\src\main\java\org\openapitools\client\infrastructure\OffsetDateTimeAdapter.kt /E/H/C/I/Y
@echo ############## finished ##############
@PAUSE
