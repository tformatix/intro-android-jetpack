@echo ############## start generating REST files ##############
@openapi-generator-cli generate -i https://tformatix.azurewebsites.net/swagger/v1/swagger.json -g kotlin -o .\swagger-generated
@echo ############## finished ##############
@PAUSE