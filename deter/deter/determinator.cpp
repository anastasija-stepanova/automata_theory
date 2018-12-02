#include "pch.h"
#include "determinator.h"
#include <vector>
#include <list>
#include <map>
#include <algorithm>

using namespace std;

determinator::determinator() 
{
}

determinator::determinator(vector<vector<vector<int>>> const &matrix, vector<int> const & finalStates)
{
	this->table = matrix;
	this->finalStates = finalStates;
	vector<int> top;
	top.push_back(0);
	queue.push_back(top);
};

determinator::~determinator()
{
}

void determinator::determinate()
{
	bool first = true;

	while (!queue.empty())
	{
		vector<int> states = queue.back();
		queue.remove(states);

		visitedStates.push_back(states);

		if (first)
		{
			first = false;
			int state = states[0];
			vector<vector<int>> row = table[state];
			vector<vector<int>> newRow;
			vector<vector<vector<int>>> newState;

			newRow.insert(newRow.end(), row.begin(), row.end());
			newState.push_back(newRow);

			result.insert({ states, newState });
			this->determinateRow(row);
		}
		else
		{
			vector<vector<int>> row = result[states][0];
			determinateRow(row);
		}
	}
	this->createNewFinalStates();
}

map<vector<int>, vector<vector<vector<int>>>> determinator::getDeterminationResult() 
{
	return this->result;
}

vector<vector<int>> determinator::getNewFinalStates() 
{
	return this->newFinalStates;
}

void determinator::createNewFinalStates() 
{
	vector<vector<int>> mapKeys;
	for (map<vector<int>, vector<vector<vector<int>>>>::iterator it = result.begin(); it != result.end(); ++it) 
	{
		mapKeys.push_back(it->first);
	}

	for (int i = 0; i < this->finalStates.size(); ++i) 
	{
		int finalState = finalStates[i];
		for (int j = 0; j < mapKeys.size(); ++j) 
		{
			vector<int> key = mapKeys[j];
			if (find(key.begin(), key.end(), finalState) != key.end()) 
			{
				if (find(newFinalStates.begin(), newFinalStates.end(), key) == newFinalStates.end()) 
				{
					this->newFinalStates.push_back(key);
				}
			}
		}
	}
	bool lol = true;
}

void determinator::determinateRow(const vector<vector<int>> &row)
{
	for (int i = 0; i < row.size(); ++i)
	{
		vector<vector<int>> newRow;
		vector<vector<vector<int>>> newState;

		vector<int> cell = row[i];

		if (cell.size() == 1)
		{
			int stateToGo = cell[0];
			vector<int> newTop;
			newTop.push_back(stateToGo);

			if ((find(visitedStates.begin(), visitedStates.end(), newTop) == visitedStates.end()) &&
				(find(queue.begin(), queue.end(), newTop) == queue.end()))
			{
				queue.push_back(newTop);
			}

			newRow.push_back(newTop);
			newState.push_back(table[cell[0]]);
			result.insert({ cell, newState });
			continue;
		}
		else if (cell.empty())
		{
			continue;
		}

		if (find(visitedStates.begin(), visitedStates.end(), cell) == visitedStates.end())
		{
			vector<vector<vector<int>>> rowsForGlue;

			for (int i = 0; i < cell.size(); ++i)
			{
				int item = cell[i];
				rowsForGlue.push_back(table[item]);
			}

			glueRows(rowsForGlue, cell);
		}
	}
}

void determinator::glueRows(vector < vector < vector < int>>> rowsForGlue, vector<int> key)
{
	vector<vector<int>> newRow;
	vector<vector<vector<int>>> newState;

	vector<vector<int>> row = rowsForGlue[0];

	for (int i = 0; i < row.size(); i++)
	{
		vector<int> cell = row[i];

		for (int j = 1; j < rowsForGlue.size(); j++)
		{
			vector<int> cellForGlue = rowsForGlue[j][i];
			for (int k = 0; k < cellForGlue.size(); ++k) {
				if (find(cell.begin(), cell.end(), cellForGlue[k]) == cell.end())
				{
					cell.push_back(cellForGlue[k]);
				}
			}
		}
		sort(cell.begin(), cell.end());
		newRow.push_back(cell);
	}
	newState.push_back(newRow);
	result.insert({ key, newState });
	if ((find(visitedStates.begin(), visitedStates.end(), key) == visitedStates.end()) &&
		(find(queue.begin(), queue.end(), key) == queue.end()))
	{
		queue.push_back(key);
	}
}
