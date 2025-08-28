# Clan Manager

Clan management web app for Clash of Clans. Good for keeping track of members, doing background checks on new players and scouting out potentials clans to join.

## Features

* General Member Stats (Donations, Trophies)
* War Performance (Average Stars, Average Destruction, Average Hit Rate)
* Detailed War History

### Tech Stack

[![SpringBoot][SpringBoot.com]][SpringBoot-url]
[![MySQL][MySQL.com]][MySQL-url]
[![React][React.js]][React-url]
[![Bootstrap][Bootstrap.com]][Bootstrap-url]

## Getting Started

### Prerequisites
- Java
- Maven
- npm

### Installation

1. Clone the repo
   ```sh
   git clone https://github.com/alexxu06/COC-CLAN-MANAGER
   ```
2. Configure and run backend server
   ```sh
   cd coc-api
   mvn spring-boot:run
   ```
3. Configure MySQL Database:
   - Install MySQL
   - Update ```application.properties``` with your database credentials:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost/{database name here}
   spring.datasource.username={user name here}
   spring.datasource.password={password here}
   ```

4. Configure and run frontend
   ```sh
   cd front-end
   npm install
   npm run dev
   ```

## Screenshots
<img width="1917" height="909" alt="Screenshot 2025-08-27 221938" src="https://github.com/user-attachments/assets/1048aba8-7f1f-4ce0-b7bf-c9a2c6ee782c" />
<img width="1914" height="904" alt="Screenshot 2025-08-27 221957" src="https://github.com/user-attachments/assets/72c8cb31-93c5-4f77-b22f-85f136e6ae1e" />
<img width="1910" height="904" alt="Screenshot 2025-08-27 222023" src="https://github.com/user-attachments/assets/99fbcae3-7def-41ff-a904-7d502066eade" />



## Credits
* [ClashKingAPI](https://api.clashk.ing/docs)
* [RoyaleAPI Developers](https://docs.royaleapi.com/proxy.html)
* [Clash of Clans API](https://developer.clashofclans.com/#/)

<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[Next.js]: https://img.shields.io/badge/next.js-000000?style=for-the-badge&logo=nextdotjs&logoColor=white
[Next-url]: https://nextjs.org/
[React.js]: https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB
[React-url]: https://reactjs.org/
[SpringBoot.com]: https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=Spring&logoColor=white
[SpringBoot-url]: https://spring.io/projects/spring-boot
[MySQL.com]: https://img.shields.io/badge/mysql-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white
[MySQL-url]: https://www.mysql.com/
[Bootstrap.com]: https://img.shields.io/badge/Bootstrap-563D7C?style=for-the-badge&logo=bootstrap&logoColor=white
[Bootstrap-url]: https://getbootstrap.com
