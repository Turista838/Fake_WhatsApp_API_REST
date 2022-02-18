# Fake_WhatsApp_API_REST

Project developed for 'Distributed Programming', a third year subject @ISEC

## Manual:

* Needs the database schema provided in [Fake_WhatsApp](https://github.com/Turista838/Fake_WhatsApp)
* Best used using [Postman](https://www.postman.com/)

## Available URI's:

| Function | Method | URI | Header | Body |
| :---         | :---           | :---          | :---          | :---          | 
| Authentication   | POST     | http://localhost:8080/session | -    | {"username": "Zé", "password": "456"}    |
| Return Contacts     | GET       | http://localhost:8080/contacts/users | -   | *token provided by authentication*    |
