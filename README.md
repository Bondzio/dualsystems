# Test task by Gabor Dicso for Dual Systems

## How to run

Clone the repository and import the projects to Eclipse. Add a WildFly 11 Server instance, then update the Targeted Runtimes in the Properties window for the ejb and web projects. Deploy and run/debug from Eclipse using the WildFly 11 instance.

## Known problems

- The EUR-HUF cache needs some optimization (see comments) and querying should be done in an async way
- date pattern, date conversion handling cleanup needed
- the managed bean InvoiceMB should be split according to functionality and should be view-scoped
- html sanitization is missing (see comment in InvoiceMB)
- user friendliness shortcomings: should use a date picker for dates, should display validation errors; when creating a new invoice, the user must add the items first, and fill invoice details as the last step, otherwise invoice details get cleared; BigDecimal values need additional formatting in tables
- according to specification, invoice items should be added in a modal dialog, this is currently done on the same page as the invoice creation itself
- should use Maven for build lifecycle management
- xhtml structure should make use of templates/includes to eliminate duplication
