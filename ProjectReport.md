# Project Report

Name: Collin Stiner

## Challenge #1 Complete

This challenge did not take me too much time, I first went through all the files in the project
and looked for something that was describing a module. I saw the `QueryEngineModule` class in the main directory and once I saw the `FakeQueryEngine` class
being binded by the `QueryEngine` interface, I simply changed it to the real `WikipediaQueryEngine` class. Once this was done,
I went ahead and tested the code and it was now retrieving real revision information at this point.

## Challenge #2 Complete

For this challenge I had to refer back to the [Guice Mental Model](https://github.com/google/guice/wiki/MentalModel) that was provided for us. The first thing I 
did was create a new class called `newRevisionFormatter`, I then just simply copied over the format() method from `RevisionFormatter` because I knew that it would be similar.
Once this was done, I created a new interface called `RevisionFormatterInterface` that is implemented by both of the formatter classes. Here I just defined one method called format that takes in
a revision as a parameter. I struggled a bit trying to get the injection to properly work but after looking at how the QueryEngineModule was setup I realized I needed to create a new module called `RevisionFormatterModule` to bind `RevisionFormatterInterface` to
`newRevisionFormatter`. I kept following the model of how `QueryEngine` was injected, so I looked for where the @Inject was used, and that was in the `WikipediaAnalyzer` class. There was an instance of `RevisionFormatter` being created here, so I went ahead and deleted that so that I can use the injected dependency.
Since the interface is binded to `NewRevisionFormatter`, I injected `RevisionFormatterInterface`, which is mapped to `NewRevisionFormatter`. Once I ran the code, I received a `ConfigurationException` from Guice. This made me go back through how `QueryEngine` is injected, and I realized that I forgot to load the configuration
in the `App` class. Once I added the new module to the `Guice.createInjector()` method, this error was resolved. I played around with different styles used by the `DateFormatter` class, I found the [DateFormatter documentation](https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html) to be quite helpful here. I decided on using the `ofLocalizedDateTime()` method because that format just looked like the most user friendly way to display a timestamp.


## Challenge #3 Complete

Challenge 3 was a bit of trial and error playing around with the `stream()` method which is something that was completely new to me. I referred back 
to the palindromes solution that professor Buis provided us with earlier in the semester. From here I knew that I needed to incorporate the .map() while using a `Collector`
to join the strings with a line break. I tried to use the `stringBuilder.append()` method that was provided in my `map()` method but this kept resulting in a string that was appending on itself each iteration. 
I then realized this was happening because the string was already being appended so I removed the `stringBuilder.append()` method from the `map()` method. After running it at this point, the result was the same as using the for loop, which is when I realized I did this correctly.

## Reflection Question #1: Functional vs OO

The major difference between the iterative and functional approach is the use of a for loop. Using a for loop seems like the most
straightforward way to solve this problem, especially considering I have not worked with the `stream()` method before. The iterative approach uses `StringBuilder` to append the new formatted revision, while the functional approach uses the stream which iterates through a collection of objects in an array. These are then joined by the `Collectors.joining()` method, which does not require StringBuilder to be used.
Another difference between these two approaches is the use of assignment. Instead of assigning the message to a variable, the functional approach uses chained methods to accomplish the same thing. The inner methods are returning a value, which is then passed into the outer methods, allowing for no assignment to be required. After seeing both approaches implemented, I think I would prefer using the iterative approach.
The use of a for loop makes it easier for me to understand how it is running through each object in the array. The code seems more readable this way, and I am much more familiar with this style of programming.

## Reflection Question #2: Polymorphism and Dependency Inversion

Polymorphism engendered dependency inversion in this application by abstracting the dependency used with modules. The higher level modules
should not have to reply on knowing what details the lower level modules need. This is achieved by abstracting the parameters used for each module.
Polymorphism is used when the modules are called from each other, using abstraction in their parameters allows for the higher level modules to work.
In this application, the dependency is inverted through the use of an interface, which allows us to abstract the methods used by a class.

