# DoUsług!

## Zasady korzystania z gita:

Branche develop i master mają włączoną ochronę przed pushowaniem tzn. działając na tym branchu nie można zrobić $git push origin master. Jak w takim razie wprowadzić tam zmiany? 

**Na początku działamy na developie dopókie nie wyjdzie jakaś pierwsza wersja apki.**

1. Od danego brancha (develop) tworzymy nowy (najlepiej przez Github) w miarę spójne nazwy np. feature/calendar.
2. Lokalnie robimy git pull 
3. Zmieniamy branch (ja to robię przez Android Studio "VCS", można poleceniem)
4. Robimy co nasze
5. Commit i push (też polecam "VCS")
* 6. Jeśli będziemy dłużej działać na tym branchu to powinniśmy go regularnie synchronizować z głównym, żeby się zbytnio nie rozjechało robimy to tak (będąc na naszym branchu:
    6.1. Nasze zmiany commitujemy
    6.2. $git pull --rebase (rebase daje to, że nasz commit pójdzie na samą górę jako najnowszy)
    6.3. $git rebase origin/develop (zmiany z develop wchodzą do naszego)
    6.4. $git push origin *nasz-branch* (najnowsza wersja na githubie)
    * Jeżeli przy pull pojawi się konflikt to trzeba rozwiązać lokalnie, polecam sprawdzać gdzie przez "VCS" jak skończymy to polecenie '$git add .' i $git push 
7. Jeśli skończymy działać na branchu i chcemy go scalić do głównego wchodzimy na w w w github kom. 
    7.1 Zakładka Pull requests -> New pull request
    7.2 Wybieramy którego do którego scalamy, potem Create pull request
    7.3 Oznaczamy wybraną osobę do zatwierdzenia (min. 1 osoba powinna przejrzeć nasze zmiany) 
    7.4 Piszemy do tego ktosia (może dodatkowy kanał na disco?)
    7.5 Ten ktoś zatwierdza nam albo pisze co warto poprawić
    * Jak poprawiamy to lokalnie, potem push i w pull requeście się odświeżą zmiany
    7.6 Na koniec scalamy.
