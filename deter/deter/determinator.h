#pragma once
#include <vector>
#include <list>
#include <map>
#include <algorithm>

using namespace std;

class determinator
{
public:
	determinator(vector<vector<vector<int>>> const & matrix, vector<int> const & finalStates);
	determinator();
	~determinator();

	void determinate();

	vector<vector<int>> getNewFinalStates();
	map<vector<int>, vector<vector<vector<int>>>> getDeterminationResult();

private:
	map<vector<int>, vector<vector<vector<int>>>> result;
	list<vector<int>> queue;
	vector<vector<vector<int>>> table;
	vector<vector<int>> visitedStates;
	vector<int> finalStates;
	vector<vector<int>> newFinalStates;

	void determinateRow(const vector<vector<int>> &row);
    void glueRows(vector<vector<vector<int>>> rowsForGlue, vector<int> key);
	void createNewFinalStates();
};

