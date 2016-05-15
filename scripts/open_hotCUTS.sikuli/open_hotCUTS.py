import sys.argv
CUTS_Name = sys.argv[1]
wait("1445228243209.png",20)
click(Pattern("1445228259341.png").similar(0.87).targetOffset(-51,1))
type(Key.F12 , Key.CTRL)
wait("1445228353848.png",10)
type(CUTS_Name+"_hot.cuts") 
click("1445228429416.png")