# Test task by Gabor Dicso for Dual Systems

## Requirements

The project was developed using the Eclipse IDE with the JBoss Tools plugin installed. The project requires WildFly 11 and PostgreSQL. You need to define a JDBC data source with the JNDI name java:/PostgresDS on the WildFly instance the app will run on.

## How to run

Clone the repository and import the projects to Eclipse. Add a WildFly 11 Server instance, then update the Targeted Runtimes in the Properties window for the ejb and web projects. Deploy and run/debug from Eclipse using the WildFly 11 instance.

## Known problems

- The EUR-HUF cache needs some optimization (see comments) and querying should be done in an async way
- Date pattern, date conversion handling cleanup needed
- The managed bean InvoiceMB should be split according to functionality and should be view-scoped
- HTML sanitization is missing (see comment in InvoiceMB)
- User friendliness shortcomings: should use a date picker for dates, should display validation errors and success messages upon invoice creation; when creating a new invoice, the user must add the items first, and fill invoice details as the last step, otherwise invoice details get cleared; BigDecimal values need additional formatting in tables
- According to the specification, invoice items should be added in a modal dialog, this is currently done on the same page as the invoice creation itself
- Should use Maven for build lifecycle management
- XHTML structure should make use of templates/includes to eliminate duplication
