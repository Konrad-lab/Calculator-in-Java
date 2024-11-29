#I was bored and make a Calculator in Java. (to run you need to have Java installed)

When you want to download the sourcecode.zip or whatever from the Releases tab windows might be says its a Trojan.
The code itself is completely safe. It's a legitimate calculator application written in Java using Swing for the GUI.
Windows Defender's false positive is likely triggered because:
        -The file contains GUI elements and event listeners
        -It creates windows and handles user input
        -It performs mathematical operations
These characteristics are sometimes similar to patterns seen in malicious software, which is why Windows Defender might be overly cautious.

![image](https://github.com/user-attachments/assets/261836e6-b7eb-47f9-858f-35f8fb68d21c)
