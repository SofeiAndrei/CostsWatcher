## How to run CostsWatcher with Silence functionality:
1. Install silence. Check this guide: https://github.com/DEAL-US/Silence/wiki/Installation
2. Pull this repo on local machine.
3. Configure Silence part:
   - Edit settings.py file with desired configuation (MySQL database credentials, Silence API port, etc).
   - There is a `create_db.sql` file that creates the database used by Silence. Run `silence createdb` command to init database.
4. Run the Silence server using the `silence run` command.
5. Configure CostsWatcher project with Silence configuration defined previously. If you modified the endpoints or changed the port on which Silence runs, update the URLs from the `PublicExpensesService` class.
6. Run CostsWatcher app. Before that, check if you have the CostsWatcher database created in PostgreSQL. If not, create the schema and run the project one time with `ddl-auto: create` option to automatically create the tables, then change the `ddl-auto` option to `validate` to keep the data on subsequent runs.

## Functionality added with Silence
- There is a new button on the home screen besides `Sign In` and `Sign Up`. It redirects the user to a page with a table indicating the average total expense amount per trip location. Internally, there's a call made to Silence which runs a SQL query for the required data.
- To submit the total expense amount of a trip (a group in our app), click the `Make expense public` button from the `Individual Expenses` tab of a group. The button will redirect you to the groups list and will make a call to Silence that adds the total expense amount in its database.

## Additional notes:
- The database used by Silence consists of a single table with two fields: `trip_location` and `trip_expense`. When the call to retrieve the table data is made, a SQL query is executed that computes the average expense amount per `trip_location`.
- In the database, the `trip_location` field is actually the group name in CostsWatcher (not a long-term solution, but it's fine for us :D).
- The total expense amount sent by clicking the `Make expense public` button is the sum of all individual expenses and all group expenses of that group (trip).
