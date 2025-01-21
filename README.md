# Flashcard Program: My First Project
by me</p>

## What is this?
This is a program written entirely in Java that allows the user to create flashcards and organize them using folders called sleeves. 
The user can move, edit, and delete the sleeves and flashcards they have created and choose which flashcards to use in a study session. </p>

## Why did I make this?
In my junior year of high school, I asked myself, "How can I show off my [still developing] Java programming skills?" I also asked myself, 
"What is something I can make that I can also use in the future?" My opinion of flashcards as an effective studying tool has changed since then --
flashcards haven't been very helpful for helping me remember ideas/concepts in the long term. But, I maintained that this program would be a useful
exercise since I had no prior experience building a user interface.</p>

## Struggles and Lessons
### Java Swing
Having never built a UI and having never used Java Swing before were both great hurdles I had to overcome. I searched the documentation for the classes, interfaces, and methods I needed to build displayable and interactive components. I also dove in blind to arranging and customizing such components -- I knew with my limited experience I could not design a masterpiece, so I focused on ensuring the components were placed and spaced in a logical manner that seemed convenient (at least for me). If I could get a do-over I would work on a smaller project first that placed more emphasis on UI so that I could gain more experience and familiarity with positioning/layouts and sizing. That way, working through the UI for this flashcard program would go much more smoothly.</p>

### Saving data between program launches
By brute forcing this portion of the project I failed to do enough research to find out about serialization and saving instances as files. Instead I made the program information about each individual flashcard in a text file, and wrote information about each individual sleeve in a separate text file; the program would then read from both of these files upon launching the program. It was a solution that functioned properly, despite its clunky moving parts, but I certainly could have saved time looking for a different solution. </p>

## Thanks
Thank you for reading this README.
