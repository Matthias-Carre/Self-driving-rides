
# input= 1st line: R C F N B T nexts lines: a b x y s f
# output= M R_0,R_i... with M number of rides and R_i ride index

#greedy
def greedy(input):
    
    return

if __name__ == '__main__':
    
    premligne = input()
    tab = premligne.split(" ")
    rides = []
    
    N = int(tab[3]) # number of rides
    F = int(tab[2]) # number of vehicles

    for i in range(N):
        rides.append(input().split(" "))

    print(rides)