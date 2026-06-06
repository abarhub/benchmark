import sys


def hello():
    print("Hello from exemple!")

def initTab(len, decalage):
    tab=[];    
    for i in range(len):
        tab2=[]
        tab.append(tab2)
        for j in range(len):            
            pos=i+j
            if decalage:
                pos+=1
            if pos%2==0:
                tab2.append(1.1)
            else:
                tab2.append(1.0)
    return tab

def multi_calcul(tab1,tab2,len):
    res=[]
    for i in range(len):
        tab_res=[]
        res.append(tab_res)
        for j in range(len):
            m=0.0
            for n in range(len):
                m+=tab1[i][n]*tab2[n][j]
            tab_res.append(m)
    return res

def multi(args:list[str]):
    lenx=3
    debug=False

    if len(args)>0:
        if args.count("--debug"):
            debug=True
            args.remove("--debug")
    
    if len(args)>0:
        s=args[0]
        n=int(s)
        lenx=n

    if debug:
        print("lenx=",lenx)

    tab1=initTab(lenx,False)
    tab2=initTab(lenx,True)

    res=multi_calcul(tab1,tab2,lenx)

    if debug:
        print("res",res)
            



def main():
    operation=2

    args=[]
    args.extend(sys.argv)
    args.pop(0)
    if len(args)>0 :
        s=args[0]
        if s=="helloworld":
            operation=1
            args.pop(0)
        elif s=="multi":
            operation=2
            args.pop(0)



    if operation==1:
        hello()
    elif operation==2:
        multi(args)


if __name__ == "__main__":
    main()
