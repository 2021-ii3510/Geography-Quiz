# Geography-Quiz

## Objectives

The objective of this application is to see how `ListViews` work. We thus have implemented an `ArrayAdapter`,
used it for our `ListView`, and implemented a `OnItemClickListener`.

Then, based on the actual quiz, we add some simple logic to verify that the answer is right or wrong.

## Modifications

I have made quite a few modifications compared to what was developed during the lecture. In particular:

- We have moved `quizCountry` as a class attribute;
- We have attached the `OnItemClickListener` in the `onCreate()` method;
- We have changed `pickRandomCountries()` to return a `List<Country>`;
- We have changed `updateDisplay(List<Country>)` to take a parameter, and it is not called from within the `pickRandomCountries()` method.