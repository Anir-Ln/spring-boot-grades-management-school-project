<style>
    .title {
        background-color: darkgreen;
    }
    tbody {
        background-color: darkslateblue;
    }
</style>


<table>
    <thead>
        <th class="title">Module</th>
        <th>Java</th>
        <th class="title">Semestre</th>
        <th>Printemps</th>
        <th class="title">Annee</th>
        <th>2021/2022</th>
        <th></th>
        <th></th>
    </thead>
    <thead>
        <th class="title">Enseignant</th>
        <th>Tarik</th>
        <th class="title">Session</th>
        <th>Normale</th>
        <th class="title">Classe</th>
        <th>GI1</th>
        <th></th>
        <th></th>
    </thead>
    <br>
    <thead>
        <th>ID</th>
        <th>CNE</th>
        <th>NOM</th>
        <th>PRENOM</th>
        <th>Element 1</th>
        <th>Element 2</th>
        <th>Moyenne</th>
        <th>Validation</th>
    </thead>
    <tbody>
        <tr>
            <th>1</th>
            <th>H1829</th>
            <th>Lahyane</th>
            <th>Mohamed</th>
            <th>16</th>
            <th>17</th>
            <th>Formule</th>
            <th>Formule</th>
        </tr>
    </tbody>    
    
</table>


## Done
+ make sure header infos are correct:
  + teacher-name (compare with one in the db)
  + study-year
  + the other elements
  + the class name is coherent
+ make sure grades in [0, 20]
+ les formules de moyenne et validation

data = array of excel data
+ get the info of the prof (module, elements, )
+ how many elements in the module
+ make sure the formulas are correct
+ get the number of the students who registered in the module
+ make sure all the data of the students are correct

+ if already imported notes ask if the teacher wants to replace them with the new grades
+ insert the grades in the db







