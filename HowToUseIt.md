# How to use the calcium calculator #

## To quickly test the calculator ##

"Use example..." buttons are provided to demonstrate the total-to-free and free-to-total cases.  Either of these populates the various fields of the user interface with test values, at which point clicking "calculate" processes them into a result shown at bottom.

## To enter a specific calculation ##

The fields at top of the calculator are required --

1) To accurately calculate buffer ionic strength, the calculator must know exactly what the buffering agent is (drop-down menu at right) and its concentration (entered as a number in the text box, units of mM assumed).

2) To determine apparent ligand-metal binding coefficients, [H+] must be known, which requires both pH and hydrogen activity to calculate.  The latter is determined indirectly, from pH, a specified buffer temperature, and buffer ionic strength.  Ionic strength is either specified ("free to total" case) or recalculated with each program loop iteration ("total to free" case).

3) In the "free to total" case, free metal concentrations are specified as "p" values, and the the necessary total concentrations calculated.  As the buffer is specified by desired end result rather than composition, an ionic strength must be chosen.  Also, as ATP is biologically active when bound to Mg, it is generally pMgATP that is of interest in physiological experiments, while the binding of free ATP to a wide variety of monovalent and divalent cations must be tracked in performing the iterative buffer calculations, hence requiring pMg up front as well, at which point pATP may be determined.

Given the above required fields, the calculator can in principle model a buffer.

Further molecular species are added using the "solute builder".  The user builds each species to be added an atom/sub-species at a time, with the drop-down menu and "add" button, e.g. to add K2CaEGTA, the user selects "K", clicks "add" twice, selects "Ca", clicks "add", and selects "EGTA" and clicks "add".  The "species added" list shows "K, K, Ca, EGTA", explicitly specifying the K2CaEGTA as a molecular species.  The user then specifies via the "population" drop-down menu whether this species is being added as a total amount ("total") or as a desired free concentration ("p" or "pME", if the species is a divalent metal assumed added in 1:1 ratio with the predominant chelator in question, typically this refers to Ca added with EGTA), and the corresponding concentration, in mM, entered.

With the solute thus specified, the user clicks "add solute to buffer" to add the solute virtually to the model buffer, and is listed in the "lines of input" box.

once all desired solutes have been added, the user clicks "calculate", and the resulting recipe/result is listed in the bottom-most box.