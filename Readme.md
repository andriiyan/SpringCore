# <h>Spring core training<h>

This is an application that has 3 entities:
- User;
- Event;
- Ticket;

Defines CRUD operation with them using in-memory storage and uses Spring, configured using XML, as IoC. Also has 
storage initialization feature.

### App's configurations

App has several profiles:
- `default`;
- `byte`;
- `json`;
- `dump`;

App could be run either using just one of the profiles or combining several profiles:
- `default`;
- `byte`, to activate edit environment variables with the `spring.profiles.active=byte`;
- `byte` and `dump`, to activate edit environment variables with the `spring.profiles.active=byte,dump`;
- `json`, to activate edit environment variables with the `spring.profiles.active=json`;
- `json` and `dump`, to activate edit environment variables with the `spring.profiles.active=json,dump` ;

Prohibited configuration pairs:
- `dump`;
- `byte`, `json` and `dump`;

#### `default` profile

Under this profile Spring is using `application.xml` IoC configuration, which defines:
- `Storage<User>` for CRUD operation;
- `Storage<Ticket>` for CRUD operation;
- `Storage<Event>` for CRUD operation;
- `BookingFacade`;
- `EventService`;
- `UserService`;
- `TicketService`;
- `EventDao`;
- `UserDao`;
- `TicketDao`;

#### `byte` profile

Under this profile Spring is using `application.xml` and `application-byte.xml` IoC configuration, which defines:
- `Storage<User>` for CRUD operation and initialization from byte file feature;
- `Storage<Ticket>` for CRUD operation and initialization from byte file feature;
- `Storage<Event>` for CRUD operation and initialization from byte file feature;
- `Serealizer` which implemented using `ByteSerealizer`;-
- `FileUtils`;
- `BookingFacade`;
- `EventService`;
- `UserService`;
- `TicketService`;
- `EventDao`;
- `UserDao`;
- `TicketDao`;

This configuration could initialize storages from the files which contain objects serialized as bytes.
Paths for initialization are taking from the `dump-byte.properties` file.


#### `json` profile

Under this profile Spring is using `application.xml` and `application-json.xml` IoC configuration, which defines:
- `Storage<User>` for CRUD operation and initialization from byte file feature;
- `Storage<Ticket>` for CRUD operation and initialization from byte file feature;
- `Storage<Event>` for CRUD operation and initialization from byte file feature;
- `Serealizer` which implemented using `JsonSerealizer`;
- `JsonInstanceCreator<User>`;
- `JsonInstanceCreator<Ticket>`;
- `JsonInstanceCreator<Event>`;
- `FileUtils`;
- `BookingFacade`;
- `EventService`;
- `UserService`;
- `TicketService`;
- `EventDao`;
- `UserDao`;
- `TicketDao`;

This configuration could initialize storages from the files which contain objects serialized as string (JSON).
Paths for initialization are taking from the `json-byte.properties` file.

#### `dump` profile

Under this profile Spring is using `application.xml` and `application-dump.xml` IoC configuration, which defines
`DumpUtils` which has `init-method` that dumps data using configuration from the `dump.properties` to dump some data. 
This profile should be used only in combination with `json` or `byte`.
