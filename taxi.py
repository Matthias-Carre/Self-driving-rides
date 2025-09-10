
# input= 1st line: R C F N B T nexts lines: a b x y s f
# output= M R_0,R_i... with M number of rides and R_i ride index

def distance(a,b,x,y):
    return abs(a-x)+abs(b-y)

#greedy
def greedy(input,F):

    return res

if __name__ == '__main__':
    
    premligne = input()
    tab = premligne.split(" ")
    rides = []
    
    N = int(tab[3]) # number of rides
    F = int(tab[2]) # number of vehicles

    for i in range(N):
        rides.append([i,input().split(" ")])


    print(rides)
    print(greedy(rides,F))


#10/09 faire full random mais il faut arriver a mettre tt les coureses sur les viucles