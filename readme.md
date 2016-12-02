## StarCitizen - File Tracker

The file tracker is a system that runs in the root directory of the Star Citizen files and creates of all included files and sub-dirs a hash; this allows us to compare new versions with each other and keep track of what changed. - Well, to be honest, the File Tracker can create these trees with hashsums for everything, it is currenlty (!) not exclusive to StarCitizen, but I hope that I can implement some features to make it more valuable for Star Citizen.

The project starts with Star Citizen Version 2.6.

# How to run?

You can build the project or simply run it in your IDE after cloning it. I am currently using the following parameters:

```
--path="E:\Program Files\Cloud Imperium Games"
--output="C:\Users\Yonas\Downloads\output.json"
--exclude=\Patcher\Config\,\Patcher\Patcher.log,\Patcher\debug.log,\StarCitizen\Public\LogBackups\,\StarCitizen\Public\client.crt,\StarCitizen\Public\system.cfg,\StarCitizen\Public\Controls\,\StarCitizen\Public\loginData.cfg,\StarCitizen\Public\Game.log,\StarCitizen\Public\USER\DATABASE\,\StarCitizen\Public\USER\Profiles\,\StarCitizen\Public\SavedGames\,\StarCitizen\Public\game.cfg
```

`path` is my path to thje installation of Star Citizen.
`output` is the path to the file that is going to contain the output.
`exclude` is a parameter to exclude dirs or files from being included. I am currently not tracking:

- \Patcher\Config\
- \Patcher\Patcher.log
- \Patcher\debug.log
- \StarCitizen\Public\ScreenShots\
- \StarCitizen\Public\LogBackups\
- \StarCitizen\Public\client.crt
- \StarCitizen\Public\system.cfg
- \StarCitizen\Public\Controls\
- \StarCitizen\Public\loginData.cfg
- \StarCitizen\Public\Game.log
- \StarCitizen\Public\USER\Database\
- \StarCitizen\Public\USER\Profiles\
- \StarCitizen\Public\USER\SavedGames\
- \StarCitizen\Public\USER\game.cfg

I am excluding these files and sub-dirs because they contain unique content that is only related to every player not to the game files itself. You can also choose to not exclude any files, even though I have to warn you, because this could compromise the security of your data. I highly recommend for security reasons to exclude: `\StarCitizen\Public\loginData.cfg` and `\StarCitizen\Public\client.crt`. There might be other files that should be excluded that I have not found yet, if you found one feel free to create an issue to suggest it.

`If you have access to the Evocati or PTU you should consider excluding these folders as well to not break any NDAs or other agreements with CIG.`