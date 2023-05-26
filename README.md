# Foreign-Exchange-Application-
Input API: https://currencyscoop.com/api-documentation

Output API: https://pastebin.com/doc_api

After you run the project with "gradle run", a Text Input Dialog is first displayed, asking the user to set a balance between 10 and 1000. If the user's input meets the requirement, the balance will be stored in the model and an Information Dialog will be displayed telling the user that the balance has been set successfully. If the user's input does not meet the requirements, an Error Dialog will be displayed. If the user has successfully set the balance, the balance can be visible in the main application window. the background music will be played automatically and the home page displays information about the selected currencies. And also shows a list of selected countries. There is a menubar that has a menuItem "About". When you click "About", a new page containing "About" information will be shown. A spinning progress indicator will appear whenever there is an HTTP request.

And there are six buttons:

1 Add currency

2 Convert two currencies

3 Clear the list

4 clear cache

5 stop the music

6 continue the music

When you click the "Add Currency" button, a world map will be displayed, and you can select a country by clicking directly on the map or selecting a country from the list of countries on the right. In the bottom right corner, there will be a small map showing a view of the selected country, when you click the "Clear/show world" button, it will turn into a world map.

There is also a button in the bottom right corner called "back to the home page" when you click it will go back to the home page. If the country you want to choose has already been selected, the system will tell you this and ask you if you want to continue to choose another country or go directly back to the home page.

When you have successfully selected a country, the map page will automatically close, then you will return to the home page. The home page will be updated with the country just selected, as will the information on the currency used in that country. If the information of the currency used by the selected country is already on the home page (some previously selected countries also use this currency), then the newly selected country will be added to the list of countries, but the list of currency information will not be changed.

When you click the "Convert two currencies" button, a new page will appear with two ComboBoxes, one for the base currency and one for the currency you want to convert to, and their options are the abbreviations of the previously selected currency. There is also an input box for filling in the amount. After you have selected and filled in all the required information, click "Convert" to get the information of the two currencies and the conversion information, which can be copied and pasted.

A lot of these pages are created when you keep hitting the "Exchange Two Currencies" button. Concurrency allows multiple currencies to be converted at the same time. For example, if you want to convert between four currencies at the same time, then the concurrency feature will come into play. 2 currencies can be exchanged at the same time.

There will be an Error Dialog to tell the user "Out of money" if the balance would go below 0 after the user clicks the convert button. If the balance would go below 0 after the user clicks the convert button, the conversion will not be performed. The balance shown on the main page will change after currency conversion.

If you want to generate a report, click "create output report", and it will return a website, this URL can be copied and pasted, open this in your browser You can see the report just generated from the URL. Click the "back" button to return to the home page. If you want to generate two or more than two different reports at the same time, then the concurrency feature will come into play. Different reports can be posted at the same time.

After the convert action, the rate of this conversion will be cached. (Only the rate and two currency Codes will be cached).

You also can click the "clear cache" button here to clear the cache.

When you click the "clear the list", all selected currencies and countries are cleared.

If you only want to delete a single currency, click the "Delete" button to the right of the currency you want to delete. When the currency is deleted, the countries that use the currency will also be deleted.

To delete a single country, click the "Delete" button to the right of the country you want to delete. When a country is deleted, the currency used by that country will not be cleared.

When you click the "clear cache", all cache will be cleared.

stop the music

continue the music

Reference:

The background music is: Bet On Me (Walk Off the Earth & D Smoke)
Walk Off the Earth. and Smoke, D., 2022. Bet On Me. [online] Pop. Available at: https://orcd.co/wotebetonme [Accessed 18 March 2022].

The CurrencyScoop link: https://currencyscoop.com/

The Pastebin link: https://pastebin.com/