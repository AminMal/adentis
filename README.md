## Adentis Interview Task ##
The task's requirement is, given a repository of `Item`s, `Product`s and `Order`s,
and some filters (such as intervals, and interval formats) from the CLI, 
extract the orders registered in a time between the intervals, and group those orders
by the product creation date, and partition them into the interval formats given in the application arguments.

### Usages ###
  * 1) Either pass order intervals as shown below:
    ```shell
    java -jar orders.jar "2018-01-01 00:00:00" "2019-01-01 00:00:00"
    ```
  * 2) Or also pass the grouping intervals (if you chose the first one, default intervals would be considered):
    ```shell
    java -jar orders.jar "2018-01-01 00:00:00" "2019-01-01 00:00:00" 0-3 4-7 \>12
    ```
  