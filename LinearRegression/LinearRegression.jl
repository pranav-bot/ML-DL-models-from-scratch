using CSV, GLM, Plots, TypedTables

data = CSV.File("./housingData.csv")

X = data.size

Y = round.(Int, data.price/1000)

Y

t = Table(X=X, Y=Y)

gr(size=(600,600))

findmax(X)
findmin(Y)

p_scatter = scatter!(X,Y,
    xlims = (0,5000),
    ylims = (0,800),
    xlabel = "Size(sqft)",
    ylabel = "Price(dollars)",
    title = "Housing Prices in Portland",
    legend = false,
    color = :red)

ols


