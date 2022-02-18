# Fake_WhatsApp_API_REST

Project developed for 'Distributed Programming', a third year subject @ISEC

## Manual:

* Needs the database schema provided in [Fake_WhatsApp](https://github.com/Turista838/Fake_WhatsApp)
* Best used with [Postman](https://www.postman.com/)

## Available URI's:

| Function | Method | URI | Header | Body |
| :---         | :---           | :---          | :---          | :---          | 
| Authentication   | POST     | http://localhost:8080/session | -    | {"username": "Zé", "password": "456"}    |
| Return Contacts     | GET       | http://localhost:8080/contacts/users | *token provided by authentication*   | -    |
| Return Groups     | GET       | http://localhost:8080/contacts/groups | *token provided by authentication*   | -    |
| Return Contacts Messages    | GET       | http://localhost:8080/messages/user/ {contact name} | *token provided by authentication*   | -    |
| Return Groups Messages     | GET       | http://localhost:8080/messages/group/ {group name} | *token provided by authentication*   | -    |
| Edit username    | PUT       | http://localhost:8080/name/ {new username} | *token provided by authentication*   | -    |
| Erase Contact     | DELETE       | http://localhost:8080/contact/ {contact name to delete} | *token provided by authentication*   | -    |
